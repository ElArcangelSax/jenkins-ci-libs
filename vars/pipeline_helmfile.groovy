def call()
{
    pipeline
    
        stages
        {
            stage('Init Process')
            {
                steps
                {
                    script
                    {
                        sh 'echo "--- Cargando Configuraciones ---"'
                        load_env("${envfile}")
                        load_env('clusters')
                        load_appconfig()
                    }
                }
            }
            stage('Pull CI')
            {
                steps
                {
                    
                        script
                        {
                            sh 'echo "---- Downloading libraries -----"'
                            pull_ci()
                        }
                
                }
            }
            stage('Obtaining Config Secrets')
            {
                steps
                {
                    
                        script
                        {
                            pull_config_helm()
                        }
                    
                }
            }
            stage('Executing Helmfile')
            {
                steps
                {
                    
                        script
                        {
                            sh 'echo "---- deploy helmfile -----"'
                            load_kube_credentials()
                            deploy_helm()
                        }
                    
                }
            }
        }
        post
        {
            always
            {
                script
                {
                    sh 'echo "----- Job Finished OK -----"'
                }
            }
            success
            {
                script
                {
                    sh 'echo "----- Action OK -----"'
                }
            }
            failure
            {
                script
                {
                    sh "echo '----- Action Failed -----'"
                }
            }
        }
    }
}