pipeline {
    agent any
    tools {
        maven 'maven-3.8'
        jdk 'openjdk-17'
    }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
//         stage('Update dependencies') {
//            steps {
//                sh 'mvn dependency:resolve'
//            }
//         }
        stage('Test, build & push image') {
            steps {
                sh 'mvn -Pdocker clean install'
            }
        }
        stage('Start service') {
            steps {
                sh '''
                docker system prune -af
                docker stop eureka-server || true && docker rm -f eureka-server || true
                docker run -p 8761:8761 -d --name eureka-server --net trungtmnguyen/devlife-eureka-server
                '''
            }
        }
    }
}