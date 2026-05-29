pipeline {

    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {

        stage('Checkout') {
            steps {

                echo 'Checking out source code...'

                git branch: 'main',
                    changelog: false,
                    poll: false,
                    url: 'https://github.com/lerndevops/it-services-portal.git'
            }
        }

        stage('Compile') {
            steps {

                echo 'Compiling application...'

                sh 'mvn clean compile'
            }
        }

        stage('Checkstyle') {
            steps {

                echo 'Running Checkstyle...'

                sh 'mvn checkstyle:checkstyle'
            }

            post {
                always {
                    recordIssues(
                        tools: [
                            checkStyle(
                                pattern: '**/target/checkstyle-result.xml'
                            )
                        ]
                    )
                }
            }
        }

        stage('Unit Test') {
            steps {

                echo 'Running unit tests...'

                sh 'mvn test'
            }

            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage') {
            steps {

                echo 'Generating JaCoCo coverage reports...'

                sh 'mvn jacoco:report'

                echo 'Verifying generated coverage files...'

                sh 'find target -name "*.xml"'
            }

            post {
                always {
                    recordCoverage(
                        tools: [
                            [
                                parser: 'JACOCO',
                                pattern: '**/target/site/jacoco/jacoco.xml'
                            ]
                        ],
                        sourceCodeRetention: 'EVERY_BUILD',
                        qualityGates: [
                            [
                                threshold: 80.0,
                                metric: 'LINE',
                                baseline: 'PROJECT'
                            ]
                        ]
                    )
                }
            }
        }

        stage('Package') {
            steps {

                echo 'Packaging application...'

                sh 'mvn package -DskipTests'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                echo 'Building Docker image...'
                withDockerRegistry(
                    credentialsId: 'DOCKER_HUB_LOGIN',
                    url: 'https://index.docker.io/v1/'
                ) {

                    sh '''
                        docker build -t docker.io/lerndevops/it-services-portal:latest .
                        docker push docker.io/lerndevops/it-services-portal:latest
                    '''
                }
            }
        }
        stage('deploy-to-dev') {
	         steps {
                    sh script:  'ansible-playbook --inventory /tmp/myinv $WORKSPACE/deploy/deploy.yaml --extra-vars "env=dev"'
           }		
        }
    }
}
