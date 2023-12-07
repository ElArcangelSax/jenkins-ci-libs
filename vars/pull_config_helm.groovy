def call()
{
    sh 'echo "Obtaining -- $kustomize_helm --"'
    withCredentials([string(credentialsId: "${env.gitcryp_config}", variable: 'AUTH')])
    {
        sh 'echo $AUTH | base64 -d > $WORKSPACE/$gitcryp_config'
    }
    //
    if ( env.debug_mode == 'true')
    {
        sh 'ls -lash $WORKSPACE'
    }
    //
    withCredentials([usernamePassword(credentialsId: "${env.git_credentials}", passwordVariable: 'password', usernameVariable: 'username')])
    {
        sh 'if [ -d $WORKSPACE/$kustomize_helm ]; then rm -fr $WORKSPACE/$kustomize_helm ; fi'
        //
        sh 'echo "Cloning repository of -- $kustomize_helm --"'
        sh ('git clone --single-branch --branch master https://$username:$password@github.com/ElArcangelSax/helmchart-n5challenge.git $kustomize_helm')
    }
    //
    if ( env.debug_mode == 'true')
    {
        sh 'ls -lash $WORKSPACE'
    }
    //
    sh '''
        cd $WORKSPACE/$kustomize_helm
        git-crypt unlock $WORKSPACE/$gitcryp_config
        echo "Deleting key already used"
        rm $WORKSPACE/$gitcryp_config
        cd $WORKSPACE
    '''
}


