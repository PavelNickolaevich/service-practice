pipeline {
    agent any

    tools {
        maven '3.8.4'
        jdk 'jdk-17'
    }

    environment {
            CUSTOM_REPO_URL = "${params.GIT_REPO_URL}"
            CUSTOM_BRANCH = "${params.GIT_BRANCH}"
            SERVER_HOST="${params.SERVER_HOST}"
            SERVER_PORT="${params.SERVER_PORT}"
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
                    branches: [[name: "refs/heads/${env.CUSTOM_BRANCH}"]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        credentialsId: 'your-git-credentials-id',
                        url: "${env.CUSTOM_REPO_URL}"
                    ]]
                ])
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn -X clean test -Dserver.port=${SERVER_PORT} -Dserver_host=$SERVER_HOST'
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