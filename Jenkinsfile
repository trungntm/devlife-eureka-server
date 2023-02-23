pipeline {
  agent any
  parameters {
    string(name: 'TARGET_BRANCH', defaultValue: 'master', description: '')
  }

  stages {
    stage('Environment') {
       steps{
           script{
            sh 'git --version'
            echo "Branch: ${env.BRANCH_NAME}"
            sh 'docker -v'
            sh 'printenv'
           }
       }
     }

    stage('Checkout') {

      steps {
        echo "Checking out source code from $TARGET_BRANCH"

        script {
          def scmVars = checkout([$class: 'GitSCM', branches: [[name: "*/$TARGET_BRANCH"]],
          userRemoteConfigs: [[url: 'https://github.com/trungntm/devlife-eureka-server.git',credentialsId:' trungntm-github-personal-token']]
        ])

          env['GIT_COMMIT'] = scmVars.GIT_COMMIT
          env['PROJECT_NAME'] = 'eureka-server'
          env['PROJECT_VERSION'] = 'n/a'
        }

        script {
          def latestCommit = sh(
            script: "git show -s ${env.GIT_COMMIT} --format=\"format:%s\"",
            returnStdout: true
          )

          echo "Latest Commit Message: ${latestCommit}"
          env['BUILD_DESCRIPTION'] = latestCommit
        }
      }
    }

    stage('Build') {
      steps {
        sh '''
          docker system prune -af
          mvn clean install
          docker stop eureka-server || true && docker rm -f eureka-server || true
          '''
      }
    }
    stage('Deploy') {
      steps {
        sh 'ocker run --name eureka-server -d -it --rm -p 8761:8761 eureka-server:latest'
      }
    }
  }

  post {
    always {
      script {
        if (env.PROJECT_NAME.isEmpty()) {
            env.PROJECT_NAME = 'devlife-next'
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
