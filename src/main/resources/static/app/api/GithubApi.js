export const fetchRepositories = () => {
    return $.get("/github/repositories");
}

export const fetchReleaseBranchesFromRepo = (repo) => {
    return $.get("/github/repositories/"+repo+"/branches/release");
}

export const fetchBranchesFromRepo = (repo) => {
    return $.get("/github/repositories/"+repo+"/branches");
}

export const fetchStoriesFromRepo = (repo,baseBranch,branch) => {
    return $.get("/github/repositories/"+repo+"/branches/diff/"+baseBranch+"/"+branch);
}
