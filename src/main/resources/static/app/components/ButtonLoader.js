import React from 'react';

class ButtonLoader extends React.Component {

    render() {
        return (
            <button type="button" className="btn btn-info pull-right"
                    name="search-github"
                    onClick={this.props.buttonAction}>
                {this.props.isLoading ? <span className='glyphicon glyphicon-refresh spinning'/> : "Search"}
            </button>
        );
    }
}

export default ButtonLoader;