pipeline {
    agent any

    stages {
        stage('Test'){
            steps {
                sh "./gradlew clean test"
            }
            post {
                always {
                    junit 'build/test-results/test/*.xml'
                    jacoco execPattern: 'build/jacoco/*.exec'
                    recordIssues (
                        tools: [
                            pmdParser(pattern:'build/reports/pmd/*.xml')
                        ]
                    )
                }
            }
        }
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/joancorderroure-gft/hello-string.git'
                // Run Graddle Wrapper
                sh "./gradlew clean test assemble"
            }
            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                success {
                    archiveArtifacts 'build/libs/*.jar'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'

            }

        }
    }
}
