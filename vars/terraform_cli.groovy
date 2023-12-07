def call(String ACTION)
{
    writeFile([file:"$WORKSPACE/terraform_cli.sh",text:libraryResource("sh/terraform_cli.sh")])
    //
    sh 'echo "Executing terraform cli"'
    sh 'chmod +x $WORKSPACE/terraform_cli.sh'
    //
    sh "$WORKSPACE/terraform_cli.sh -a $ACTION"
    //
    sh 'echo "Deleting already used configuration"'
    sh "rm $WORKSPACE/terraform_cli.sh"

}