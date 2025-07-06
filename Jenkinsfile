pipeline {
    agent any

    environment {
        NAUKRI_EMAIL = credentials('naukri-email')       // Jenkins Credentials ID
        NAUKRI_PASSWORD = credentials('naukri-password') // Jenkins Credentials ID
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/ShubhamPuri-SDET/Automation-portfolio.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                echo '🛠️ Building project...'
                sh 'mvn clean compile'
            }
        }

        stage('Run Resume Upload Automation') {
            steps {
                echo '🚀 Running resume upload test...'
                sh 'mvn test'
            }
        }
    }

    post {
        success {
            echo '✅ Resume uploaded successfully via automation.'
        }
        failure {
            echo '❌ Automation failed. Check console logs for errors.'
        }
    }
}
