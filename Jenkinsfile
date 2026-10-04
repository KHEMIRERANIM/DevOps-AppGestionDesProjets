pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build Docker Images') {
            steps { sh 'docker compose build' }
        }
        stage('Deploy avec Docker Compose') {
            steps {
                sh 'docker compose down || true'
                sh 'docker compose up -d'
            }
        }
        stage('Verification') {
            steps {
                sh 'sleep 40'
                sh 'docker compose ps'
                sh 'curl -f http://localhost:8080/entreprise/all'
            }
        }
    }
    post {
        failure { sh 'docker compose logs --tail=100' }
    }
}
