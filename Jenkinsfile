pipeline {
    agent any

    tools {
        maven '3.8.4'
        jdk 'jdk-17'
    }

    environment {
            CUSTOM_REPO_URL = "${params.GIT_REPO_URL}"
            CUSTOM_BRANCH = "${params.GIT_BRANCH}"
        }

    stages {

    stage('Debug') {
        steps {
            echo "Repository URL: ${env.CUSTOM_REPO_URL}"
            echo "Branch: ${env.CUSTOM_BRANCH}"
        }
    }
        stage('Cleaning workspace') {
            steps {
                cleanWs()
                script {
                    currentBuild.displayName = "Тестовая сборка: ${currentBuild.number}"
                }
            }
        }

        stage('Checkout repository') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: env.CUSTOM_BRANCH]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        credentialsId: 'your-git-credentials-id',
                        url: env.CUSTOM_BRANCH
                    ]]
                ])
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test'
            }

            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'target/**/*.xml,target/**/*.txt', allowEmptyArchive: true
        }
    }
}