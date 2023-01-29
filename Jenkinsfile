pipeline {
    agent {
        docker {
            image 'maven:3.8.7-eclipse-temurin-17'
            args '-v /root/.m2:/root/.m2'
        }
    }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Update dependencies') {
           steps {
               sh 'mvn dependency:resolve'
           }
        }
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Build Image') {
            steps {
                sh 'mvn -Pdocker clean install'
            }
        }
        stage('Run') {
            steps {
                sh 'docker stop devlife-eureka-server || true && docker rm -f devlife-eureka-server || true'
                sh 'docker run -p 8761:8761 -d --name devlife-eureka-server trungtmnguyen/devlife-eureka-server:latest'
            }
        }
    }
}