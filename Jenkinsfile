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
        stage('Update dependencies') {
           steps {
               sh 'mvn dependency:resolve'
           }
        }
        stage('Test, build & push image') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Start service') {
            steps {
                sh '''
                docker-compose -f /root/devlife down eureka-server
                docker-compose -f /root/devlife up -d eureka-server
                '''
            }
        }
    }
}