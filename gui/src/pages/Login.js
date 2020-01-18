import React from 'react';

export default class Login extends React.Component {
    constructor(props){
        super(props);
        this.state= {
            email: 'admin@admin.pl',
            pwd: 'user'
        }
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangePwd = this.handleChangePwd.bind(this);
    }

    handleChangeEmail(event){
        this.setState({email: event.target.value})
    }
    handleChangePwd(event){
        this.setState({pwd: event.target.value})
    }
    signIn() {
        fetch("http://localhost:8080/api/auth/login", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email: this.state.email,
                password: this.state.pwd,
            }),
        })
            .then(response => response.json())
            .then(currentUser => {

                this.props.setCurrentUser(currentUser);
                this.props.history.push('/account');

            })
            .catch(error => console.log('error', error));
    }

    render(){


        return <div>
            <h1>Sign Up</h1>
            <p>Please fill in this form to create an account.</p>
            <hr></hr>

            <label htmlFor="email"><b>Email</b></label>
            <input type="email" placeholder="Enter Email" name="email" value={this.state.email} onChange={this.handleChangeEmail} required/>

            <label htmlFor="psw"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" value={this.state.pwd} onChange={this.handleChangePwd} required/>


            <div className="clearfix">
                <button type="submit" onClick={this.signIn.bind(this)} className="signupbtn">Sign Up</button>
            </div>

        </div>
    }
}
