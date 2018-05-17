import React from 'react';
import List from "../components/List";
import Select from "../components/Select";
import {
    fetchRepositories,
    fetchReleaseBranchesFromRepo,
    fetchBranchesFromRepo,
    fetchStoriesFromRepo
} from "../api/GithubApi";
import ButtonLoader from "../components/ButtonLoader";

class GithubContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            stories: [],
            repositories: [],
            releasedBranches: [],
            branches: [],
            repoSelectValue: "",
            branchReleaseSelectValue: "",
            branchSelectValue: "",
            isButtonLoading: false

        };
    }

    componentDidMount() {
        fetchRepositories()
            .then(data => this.setState({repositories: data}))
    }

    handleRepoChange(value) {
        this.setState({isButtonLoading: true});
        fetchReleaseBranchesFromRepo(value)
            .then(data =>
                this.setState({
                    releasedBranches: data,
                    repoSelectValue: value
                }))
            .then(() =>
                fetchBranchesFromRepo(value)
                    .then(data => this.setState({
                        branches: data,
                        isButtonLoading: false
                    })));
    }

    handleReleaseBranchChange(value) {
        this.setState({branchReleaseSelectValue: value});
    }

    handleBranchChange(value) {
        this.setState({branchSelectValue: value});
    }

    searchStories() {
        this.setState({isButtonLoading: true});
        fetchStoriesFromRepo(this.state.repoSelectValue,
            this.state.branchReleaseSelectValue,
            this.state.branchSelectValue)
            .then(data => {
                this.setState({
                    stories: data.stories,
                    isButtonLoading: false
                });
            })
    }

    render() {
        return (
            <div>
                <div className="row">
                    <div className="col-lg-6 no-padding">
                        <Select data={this.state.repositories}
                                handleSelectChange={this.handleRepoChange.bind(this)}
                                value={this.state.repoSelectValue}
                        />
                    </div>
                </div>
                <div className="row">
                    <div className="col-lg-4 no-padding">
                        <Select data={this.state.releasedBranches}
                                handleSelectChange={this.handleReleaseBranchChange.bind(this)}
                                value={this.state.branchReleaseSelectValue}
                        />
                    </div>
                    <div className="col-lg-4 no-padding">
                        <Select data={this.state.branches}
                                handleSelectChange={this.handleBranchChange.bind(this)}
                                value={this.state.branchSelectValue}
                        />
                    </div>
                    <div className="col-lg-2 pull-right no-padding">
                        <ButtonLoader
                            isLoading={this.state.isButtonLoading}
                            buttonAction={this.searchStories.bind(this)}
                        />
                    </div>
                </div>
                <div className="row">
                    <List data={this.state.stories}/>
                </div>
            </div>

        );
    }
}

export default GithubContainer;