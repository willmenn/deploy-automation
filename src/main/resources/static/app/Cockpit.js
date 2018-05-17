import React from 'react';
import ReactDOM from 'react-dom';

import GithubContainer from "./container/GithubContainer";
import JiraContainer from "./container/JiraContainer";

const refreshReact = () => {
    ReactDOM.render(
        <Cockpit/>,
        document.getElementById('cockpit')
    );
};

class Cockpit extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="container">
                <h1>Deploy Visualization!</h1>
                <div className="row">
                    <div className="col-lg-6">
                        <GithubContainer/>
                    </div>
                    <div className="col-lg-6">
                        <JiraContainer/>
                    </div>
                </div>
            </div>
        );
    }
}

refreshReact();

