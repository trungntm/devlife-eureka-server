pipeline {
    agent any
    tools {
        maven 'maven-3.6'
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

        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Build Image') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}