// Jenkinsfile - Pipeline using the shared library
@Library('my-shared-lib') _

pipeline {
    agent any
    
    parameters {
        string(name: 'BRANCH', defaultValue: 'main', description: 'Branch to checkout')
        string(name: 'REPO_URL', defaultValue: 'https://github.com/your-org/your-repo.git', description: 'Repository URL')
    }
    
    stages {
        stage('Checkout Code') {
            steps {
                script {
                    gitCheckout([
                        repoUrl: params.REPO_URL,
                        branch: params.BRANCH,
                        credentialsId: 'your-git-credentials'
                    ])
                }
            }
        }
        
        stage('Build') {
            steps {
                echo "Building the application..."
                // Add your build steps here
            }
        }
        
        stage('Test') {
            steps {
                echo "Running tests..."
                // Add your test steps here
            }
        }
    }
    
    post {
        always {
            echo "Pipeline completed"
        }
        success {
            echo "Pipeline succeeded"
        }
        failure {
            echo "Pipeline failed"
        }
    }
}