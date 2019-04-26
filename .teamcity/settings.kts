import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.2"

project {

    buildType(Build)
}

object Build : BuildType({
    name = "Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    params {
        password("liquibasePassword", "credentialsJSON:c0f482be-b812-4979-8460-21a077f3ac2f")
    }

    steps {
        script {
            name = "Capture Start Time"
            scriptContent = "export START_TIME=$(date)"
        }
        script {
            name = "Liquibase version"
            scriptContent = "liquibase --version"
        }
        script {
            name = "Liquibase validate"
            scriptContent = "liquibase --logLevel=debug --password=%liquibasePassword% --defaultsFile=cf-mysql-01.properties validate"
        }
        script {
            name = "Liquibase update"
            scriptContent = "liquibase --logLevel=debug --password=%liquibasePassword% --defaultsFile=cf-mysql-01.properties update"
        }
        script {
            name = "Send Metrics"
            scriptContent = "curl -k -u \"x:edca663a-18da-4532-a766-e8726545ce4c\" https://localhost:8088/services/collector/event -d '{\"sourcetype\": \"teamcity\", \"event\": { \"message\":\"Build Completed\", \"startTime\": \"\${START_TIME}\", \"endTime\": \"\$(date)\"}}'"
        }
    }

    triggers {
        vcs {
        }
    }
})
