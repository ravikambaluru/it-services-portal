pipeline{

    agent {
        label 'jenkins-agent'
    }

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    stages {
        stage('Clean WS'){

            steps {
                cleanWs();
            }

        }
        stage('Clone Repo'){
            steps {
                git branch: 'main', url: 'https://github.com/ravikambaluru/it-services-portal'
            }
        }
        stage('Compile Code'){

            steps {
                sh 'mvn compile'
            }

        }
    }

}
