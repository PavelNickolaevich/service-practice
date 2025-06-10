pipeline {
    agent any

    tools {
        maven '3.8.4'
        jdk 'jdk-17'
    }

 parameters {
         string(name: 'GIT_REPO_URL', defaultValue: 'https://github.com/PavelNickolaevich/service-practice.git', description: 'Git Repository URL')
         string(name: 'GIT_BRANCH', defaultValue: 'main', description: 'Git Branch')
     }

    stages {
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
                    branches: [[name: env.GIT_BRANCH]],
                    doGenerateSubmoduleConfigurations: false,
                    extensions: [],
                    submoduleCfg: [],
                    userRemoteConfigs: [[
                        credentialsId: 'your-git-credentials-id',
//                         url: 'https://github.com/PavelNickolaevich/service-practice.git'
                        url: env.GIT_REPO_URL
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