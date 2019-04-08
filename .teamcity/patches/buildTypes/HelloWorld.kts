package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'HelloWorld'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("HelloWorld")) {
    params {
        add {
            password("env.LIQUIBASE_PASSWORD", "credentialsJSON:adc90461-ee37-48ca-ac34-6d7bf901dd25")
        }
    }

    vcs {
        add(DslContext.settingsRoot.id!!)
    }

    expectSteps {
        script {
            scriptContent = "env && liquibase --defaultsFile=cf-mysql-01.properties migrate"
        }
    }
    steps {
        update<ScriptBuildStep>(0) {
            scriptContent = "liquibase --verbose --password=%env.LIQUIBASE_PASSWORD% --defaultsFile=cf-mysql-01.properties update"
        }
    }

    triggers {
        add {
            vcs {
                branchFilter = ""
            }
        }
    }
}
