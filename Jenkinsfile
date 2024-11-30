pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/adisharma-explores/authentication-microservice.git' // Replace with your GitHub repo URL
        DOCKER_IMAGE = 'auth-service' // Replace with your Docker image name
        CONTAINER_NAME = 'booking-service-container' // Replace with desired container name
        DOCKER_FOLDER= './docker/Docker'
        DOCKER_USER='adisharmaa'
        DOCKER_REGISTRY='docker.io'
        AZURE_REGISTRY='dockermicroservices.azurecr.io'
        ACR_NAME = 'dockermicroservices'
        IMAGE_NAME = 'microservice/auth-server'
        IMAGE_TAG = 'latest'
        AZURE_APP= 'auth-server-micro'
        AZURE_SUBSCRIPTION = 'DefaultResourceGroup-CID'
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning the repository...'
                git branch: 'main', credentialsId: 'git-pat', url: "${REPO_URL}"
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    bat "docker build -f ${DOCKER_FOLDER} -t ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ."
                }
            }
        }

        // stage('Run Docker Container') {
        //     steps {
        //         echo 'Running Docker container...'
        //         script {
        //             // Stop and remove the existing container if it exists
        //             bat """
        //             docker stop ${CONTAINER_NAME} || true
        //             docker rm ${CONTAINER_NAME} || true
        //             docker run -d --name ${CONTAINER_NAME} -p 7443:7443 ${DOCKER_IMAGE}:${env.BUILD_NUMBER}
        //             """
        //         }
        //     }
        // }
        // stage('Login to Docker Hub') {
        //     steps {
        //         withCredentials([usernamePassword(credentialsId: 'docker-token',
        //                                           usernameVariable: 'DOCKER_USERNAME',
        //                                           passwordVariable: 'DOCKER_PASSWORD')]) {
        //             script {
        //                 bat """
        //                     docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD} ${DOCKER_REGISTRY} || exit 1
        //                 """
        //             }
        //         }
        //     }
        // }
        // stage('push Docker Container') {
        //     steps {
        //         echo 'Running Docker container...'
        //         script {
        //             // Stop and remove the existing container if it exists
        //             bat """
        //             docker tag ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ${DOCKER_REGISTRY}/${DOCKER_USER}/${DOCKER_IMAGE}:${env.BUILD_NUMBER}
        //             docker push ${DOCKER_REGISTRY}/${DOCKER_USER}/${DOCKER_IMAGE}:${env.BUILD_NUMBER}

        //             docker tag ${DOCKER_IMAGE}:${env.BUILD_NUMBER} ${DOCKER_REGISTRY}/${DOCKER_USER}/${DOCKER_IMAGE}:latest
        //             docker push ${DOCKER_REGISTRY}/${DOCKER_USER}/${DOCKER_IMAGE}:latest
        //             """
        //         }
        //     }
        // }
        stage('Azure container registry Login') {
            steps {
                withCredentials([azureServicePrincipal(credentialsId: "az-creds",
                                                           clientIdVariable: 'AZURE_CLIENT_ID',
                                                           clientSecretVariable: 'AZURE_CLIENT_SECRET',
                                                           tenantIdVariable: 'AZURE_TENANT_ID')]){
                    script {
                        bat """
                            az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET --tenant $AZURE_TENANT_ID
                        """
                    }
                }
            }
        }
         stage('login to Azure container') {
            steps {
                echo 'Running Docker container...'
                script {
                    // Stop and remove the existing container if it exists
                    bat """

                            az acr login --name ${ACR_NAME}
                    """
                }
            }
        }
        stage('push Docker Container to Azure') {
            steps {
                echo 'Running Docker container...'
                script {
                    // Stop and remove the existing container if it exists
                    bat """

                            docker tag ${DOCKER_IMAGE}:${env.BUILD_NUMBER}  ${AZURE_REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER}
                            docker push ${AZURE_REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER}
                    """
                }
            }
        }
        stage('Azure app Login') {
            steps {
                withCredentials([azureServicePrincipal(credentialsId: "azure-app",
                                                           clientIdVariable: 'AZURE_CLIENT_ID',
                                                           clientSecretVariable: 'AZURE_CLIENT_SECRET',
                                                           tenantIdVariable: 'AZURE_TENANT_ID')]){
                    script {
                        bat """
                            az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET --tenant $AZURE_TENANT_ID
                        """
                    }
                }
            }
        }
        stage('Update App Configuration') {
            steps {
                script {
                    bat """
                        az webapp config container set --name ${AZURE_APP} --resource-group ${AZURE_SUBSCRIPTION} --docker-custom-image-name ${AZURE_REGISTRY}/${IMAGE_NAME}:${env.BUILD_NUMBER}
                    """
                }
            }
        }

        stage('Azure app restart') {
          steps {
                withCredentials([azureServicePrincipal(credentialsId: "az-creds",
                                                          clientIdVariable: 'AZURE_CLIENT_ID',
                                                          clientSecretVariable: 'AZURE_CLIENT_SECRET',
                                                          tenantIdVariable: 'AZURE_TENANT_ID')]){
                    script {
                        bat """
                            az webapp restart --name ${AZURE_APP} --resource-group ${AZURE_SUBSCRIPTION}
                        """
                    }
                }
            }
        }
        // stage('Update Staging Slot') {
        //     steps {
        //         script {
        //             bat """
        //                 az webapp config container set --name ${AZURE_APP} --resource-group ${AZURE_SUBSCRIPTION} --slot staging --docker-custom-image-name ${AZURE_REGISTRY}/${IMAGE_NAME}:latest
        //             """
        //         }
        //     }
        // }
        // stage('Restart Staging Slot') {
        //     steps {
        //         script {
        //             bat """
        //                 az webapp restart  --name ${AZURE_APP}  --resource-group ${AZURE_SUBSCRIPTION} --slot staging
        //             """
        //         }
        //     }
        // }
        // stage('Swap Deployment Slots') {
        //     steps {
        //         script {
        //             bat """
        //                 az webapp deployment slot swap  --name ${AZURE_APP}  --resource-group ${AZURE_SUBSCRIPTION} --slot staging
        //             """
        //         }
        //     }
        // }



    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline execution failed.'
        }
    }
}