pipeline {
    agent any

    environment {
        NAUKRI_EMAIL = credentials('naukri-email')     // Jenkins credential ID
        NAUKRI_PASSWORD = credentials('naukri-password')
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ShubhamPuri-SDET/Automation-portfolio.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling Java classes...'
                sh 'mvn clean compile'
            }
        }

        stage('Test - Upload Resume') {
            steps {
                echo 'Running resume upload automation...'
                sh 'mvn test'
            }
        }
    }

    post {
        success {
            echo '✅ Resume upload job completed successfully!'
        }
        failure {
            echo '❌ Job failed! Check the console output for details.'
        }
    }
}
