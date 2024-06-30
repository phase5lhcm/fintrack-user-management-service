def repoUrl = 'https://github.com/phase5lhcm/fintrack-user-management-service'
def branch = 'main'
def buildScript = 'mvn clean package'
def testScript = 'mvn test'
def deployScript = './deploy.sh'

pipeline {
    agent any

    environment {
        REPO_URL = "${repoUrl}"
        BRANCH = "${branch}"
        BUILD_SCRIPT = "${buildScript}"
        TEST_SCRIPT = "${testScript}"
        DEPLOY_SCRIPT = "${deployScript}"
        SONARQUBE_SCANNER_HOME = tool 'SonarQube Scanner'
        SONARQUBE_URL = 'http://localhost:9000'
        SONARQUBE_TOKEN = credentials('sonarqube-token')
    }

    stages {
        stage('Checkout') {
            steps {
                git url: "${REPO_URL}", branch: "${BRANCH}"
            }
        }

        stage('Build') {
            steps {
                sh "${BUILD_SCRIPT}"
            }
        }

        stage('Test') {
            steps {
                sh "${TEST_SCRIPT}"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=your_project_key \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=${SONARQUBE_TOKEN}
                    """
                }
            }
        }

        stage('Deploy') {
            steps {
                sh "${DEPLOY_SCRIPT}"
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
