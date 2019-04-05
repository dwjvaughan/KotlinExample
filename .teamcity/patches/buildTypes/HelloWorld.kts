package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
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

    triggers {
        add {
            vcs {
                branchFilter = ""
            }
        }
    }
}
