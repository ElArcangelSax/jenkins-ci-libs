def call()
{
    pipeline
    {
        stages
        {
            stage('Verificate Data')
            {
                steps
                {
                    script
                    {
                        sh 'echo "---- Verificated NO NULL VALUES"'
                        if (envfile.empty)
                        {
                            sh 'echo "You need the envfile"'
                            sh 'exit 1'
                        }
                        if (ACCOUNT.empty)
                        {
                            sh 'echo "You need the  ACCOUNT"'
                            sh 'exit 1'
                        }
                        if (ACTION.empty)
                        {
                            sh 'echo "You need the ACTION"'
                            sh 'exit 1'
                        }
                        if (INPUT.empty)
                        {
                            sh 'echo "You need the INPUT"'
                            sh 'exit 1'
                        }
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
                        load_env("${envfile}")
                        load_env("${ACCOUNT}")
                        pull_ci()
                        pull_ci_env("${ACCOUNT}")
                    }
                }
            }
        }
            stage('Obtain credencials')
            {
                steps
                {
                    
                        script
                        {
                            generate_credentials_azure_export()
                        }
                    
                }
            }
            stage('Executing terraform-cli')
            {
                steps
                {
                    
                        script
                        {
                            sh 'echo "---- Executing terraform -----"'
                            generate_credentials_git_ssh('github')
                            terraform_cli("${action}")
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
                    sh 'echo "----- Job Finished -----"'
                }
            }
            success
            {
                script
                {
                    sh 'echo "----- The action has been executed successfully -----"'
                }
            }
            failure
            {
                script
                {
                    sh 'echo "----- Action Failed -----"'
                }
            }
        }
    }
}