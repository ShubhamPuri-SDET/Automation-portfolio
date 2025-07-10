pipeline {
    agent any

    environment {
        NAUKRI_EMAIL      = credentials('naukri-email')       // Jenkins Credentials ID
        NAUKRI_PASSWORD   = credentials('naukri-password')
        
        LINKEDIN_EMAIL    = credentials('linkedin-email')     // Credentials ID in Jenkins
        LINKEDIN_PASSWORD = credentials('linkedin-password')
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

        stage('Run Automation Tests') {
            steps {
                echo '🚀 Running LinkedIn and Naukri automation tests...'
                // Run only specific test classes
                sh 'mvn -Dtest=LinkedinRequest,UpdateResume test'
            }
        }
    }

    post {
        success {
            echo '✅ Automation scripts ran successfully (LinkedIn + Naukri).'
        }
        failure {
                    echo '❌ Automation failed. Check Jenkins console logs for errors.'
        }
    }
}
