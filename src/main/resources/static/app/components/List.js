import React from 'react';

class List extends React.Component {

    render() {
        return (
            <div className="list-group-item">
                {this.props.data.length > 0 ? this.props.data.map(name =>
                    <div className="list-group-item">
                        <h5 className="list-group-item-heading">
                            {name}
                        </h5>
                    </div>
                )
                    : <div className="list-group-item">
                        <h5 className="list-group-item-heading">
                             No data selected
                        </h5>
                    </div>}
            </div>
        );
    }
}

export default List;