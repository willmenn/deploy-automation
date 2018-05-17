import React from 'react';
import List from "../components/List";
import {fetchStories} from "../api/JiraApi";
import ButtonLoader from "../components/ButtonLoader";

class JiraContainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            stories: [],
            iterationNumber: "",
            isButtonLoading: false
        };
    }

    handleInputChange(event) {
        this.setState({iterationNumber: event.target.value});
    }

    searchStories() {
        this.setState({isButtonLoading: true});
        fetchStories(this.state.iterationNumber)
            .then(data => {
                this.setState({
                    stories: data,
                    isButtonLoading: false
                });
            })
    }

    render() {
        return (
            <div>
                <div className="row">
                    <label className="col-sm-2 col-form-label">Iteration :</label>
                    <div className="col-sm-6">
                        <input value={this.state.value}
                               onChange={this.handleInputChange.bind(this)}
                               className="form-control"
                               id="iterationNumber"/>
                    </div>
                    <div className="col-lg-2 pull-right">
                        <ButtonLoader
                            isLoading={this.state.isButtonLoading}
                            buttonAction={this.searchStories.bind(this)}
                        />
                    </div>
                </div>
                <List data={this.state.stories}/>
            </div>
        );
    }
}

export default JiraContainer;