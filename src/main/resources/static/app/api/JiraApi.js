export const fetchStories = (iteration) => {
    return $.get("/jira/iterations/" + iteration + "/done/");
}