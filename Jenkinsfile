pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'reslacannemozze/freestyle-persistence-service:latest'
        EC2_USER = 'ec2-user'
        EC2_HOST = 'ec2-34-247-173-32.eu-west-1.compute.amazonaws.com'
    }

    stages {
        stage('Debug Workspace') {
    sh 'pwd'
    sh 'ls -la'
}
 stage('Clean Workspace') {
        deleteDir() // Elimina tutto il contenuto del workspace
    }

        stage('Checkout') {
                    checkout scm
                            }

        stage('Build & Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_hub_reslacannemozze', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        sh "docker build -t ${DOCKER_IMAGE} ."
                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                        sh "docker push ${DOCKER_IMAGE}"
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2_ssh', keyFileVariable: 'SSH_KEY')]) {
                    script {
                        sh """
                        ssh -o StrictHostKeyChecking=no -i $SSH_KEY ${EC2_USER}@${EC2_HOST} << 'EOF'
                            docker pull ${DOCKER_IMAGE}
                            docker stop app || true
                            docker rm app || true
                            docker run -d --name app -p 8080:8080 ${DOCKER_IMAGE}
                        EOF
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo '🚀 Deploy completato con successo!'
        }
        failure {
            echo '❌ Deploy fallito!'
        }
    }
}
