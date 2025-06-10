pipeline {
    agent any

    tools {
        maven '3.8.4'
        jdk 'jdk-17'
    }

 parameters {
         string(name: 'GIT_REPO_URL', description: 'Git Repository URL')
         string(name: 'GIT_BRANCH', description: 'Git Branch')
     }

    stages {

    stage('Debug') {
        steps {
            echo "Repository URL: ${params.GIT_REPO_URL}"
            echo "Branch: ${params.GIT_BRANCH}"
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
                    branches: [[name: params.GIT_BRANCH]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        credentialsId: 'your-git-credentials-id',
//                         url: 'https://github.com/PavelNickolaevich/service-practice.git'
                        url: params.GIT_REPO_URL
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