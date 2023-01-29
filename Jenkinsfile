pipeline {
  agent any
    parameters {
      string(name: 'TARGET_BRANCH', defaultValue: 'master', description: '')
    }

    environment {
       DOCKER_ARGS = "--net=\"host\" -v /home/jenkins/.m2:/.m2"
    }

    stages {

      stage('Build') {
        agent {
            docker {
                image 'openjdk:17-alpine'
                label 'master'
                args "$DOCKER_ARGS"
            }
        }

        steps {

            withMaven {
                sh 'mvn -Pdocker clean install'
            }
        }
      }


    }

    post {
        always {
            script {
                if (env.PROJECT_NAME.isEmpty()) {
                    env.PROJECT_NAME = 'eureka-server'
                }

                if (env.PROJECT_VERSION.isEmpty()) {
                    env.PROJECT_VERSION = 'n/a'
                }

                // in case of failure, we'd like to have simple 'git blame' on build history :)
                currentBuild.displayName = "${env.PROJECT_NAME} - ${env.PROJECT_VERSION} [$TARGET_BRANCH]"
                buildDescription("${env.BUILD_DESCRIPTION}")
            }
        }
    }
}
