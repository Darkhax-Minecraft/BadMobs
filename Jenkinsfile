#!/usr/bin/env groovy

pipeline {

    agent any
    
    stages {
    
        stage('Clean') {
        
            steps {
            
			    withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
				
                    echo 'Cleaning project workspace.'
                    sh 'chmod +x gradlew'
				    sh './gradlew clean'
				}
            }
        }
        
        stage('Build') {
        
		    steps {
			
			    withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
			
			        echo 'Building project.'
                    sh './gradlew build --stacktrace --info'
			    }
			}
        }
		
		stage('Maven') {
		
		    steps {
			
			    withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
			
			        echo 'Deploying to Maven.'
                    sh './gradlew publish --stacktrace --info'
			    }
			}
		}
		
		stage('CurseForge') {
		    steps {
			
			    withCredentials([file(credentialsId: 'mod_build_secrets', variable: 'ORG_GRADLE_PROJECT_secretFile')]) {
			
			        echo 'Deploying to CurseForge.'
                    sh './gradlew curseforge --stacktrace --info'
			    }
			}
		}
    }
}