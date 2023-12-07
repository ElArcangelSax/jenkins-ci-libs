def call(String ENVFILE)
{
    writeFile([file:"$WORKSPACE/cicd/${ENVFILE}",text:libraryResource("env_load/${ENVFILE}")])
    sh "echo Loading environment -- ${ENVFILE} --"
    load "$WORKSPACE/cicd/${ENVFILE}"
    sh 'echo "Deleting already used configuration"'
    sh "rm $WORKSPACE/cicd/${ENVFILE}"
    //
    // load_docker_images()
}