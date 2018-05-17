import React from 'react';

class Select extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            value: ""
        };
    }

    handleChange(event) {
        this.setState({value: event.target.value});
        this.props.handleSelectChange(event.target.value);
    }

    render() {
        return (
            <select className="selectpicker form-control" name="product"
                    value={this.state.value} onChange={this.handleChange.bind(this)}>
                <option disabled selected value> -- select an option --</option>
                {this.props.data.map(name =>
                    <option className="dropdown-item" href="#">{name}</option>
                )}
            </select>
        );
    }
}

export default Select;