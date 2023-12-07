def call()
{
    sh 'echo "-------- Loading libraries -----------------"'
    //
    withCredentials([string(credentialsId: "${gitcryp_ci}", variable: 'AUTH')])
    {
        sh 'echo $AUTH | base64 -d > $WORKSPACE/$gitcryp_ci'
    }
    //
    if ( env.debug_mode == 'true')
    {
        sh 'ls -lash $WORKSPACE'
    }
    //
    withCredentials([usernamePassword(credentialsId: "${env.git_credentials}", passwordVariable: 'password', usernameVariable: 'username')])
    {
        sh 'if [ -d $WORKSPACE/$cicd ]; then rm -fr $WORKSPACE/$cicd ; fi'
        //
        sh ('git clone --single-branch --branch master https://$username:$password@github.com/ElArcangelSax/jenkins-ci-libs.git $cicd')
        //
    }
    //
    if ( env.debug_mode == 'true')
    {
        sh 'ls -lash $WORKSPACE'
    }
    //
    sh '''
        cd $WORKSPACE/$cicd
        git-crypt unlock $WORKSPACE/$gitcryp_ci
        echo "Borrando key ya utilizada"
        rm $WORKSPACE/$gitcryp_ci
        cd $WORKSPACE
    '''
    //
    sh 'echo "---- Loading Libraries -----"'
    load "$WORKSPACE/$cicd/resources/env/$envfile"
    sh 'if [ -d $WORKSPACE/$cicd/.git ]; then rm -fr $WORKSPACE/$cicd/.git ; fi'
}