pipeline{

    agent any

    tools {
        jdk 'JDK21'
        mvn 'Maven3'
    }

    stages {
        stage('Clean WS'){
            cleanWs();
        }
        stage('Clone Repo'){
            git branch: 'main', url: 'https://github.com/ravikambaluru/it-services-portal'
        }
        stage('Compile Code'){
            sh 'mvn compile'
        }
    }

}
