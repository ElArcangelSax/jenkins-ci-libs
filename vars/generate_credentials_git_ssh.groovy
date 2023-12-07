def call(String ENVIRONMENT)
{
    writeFile([file:"$WORKSPACE/generate_credentials_git_ssh.sh",text:libraryResource("sh/generate_credentials_git_ssh.sh")])
    sh "chmod +x $WORKSPACE/generate_credentials_git_ssh.sh"
    //
    sh "echo Generating Credentials for... '$ENVIRONMENT'"
    sh "$WORKSPACE/generate_credentials_git_ssh.sh ${github_user_mail} '$ENVIRONMENT'"
    //
    sh 'echo "Deleting already used configuration."'
    sh 'rm $WORKSPACE/generate_credentials_git_ssh.sh'
}