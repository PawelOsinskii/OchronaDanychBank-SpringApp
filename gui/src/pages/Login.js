import React from 'react';

export default class Login extends React.Component {
    constructor(props){
        super(props);
        this.state= {
            email: 'admin@admin.pl',
            pwd: 'admin'
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
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", "Basic Z2xlZS1vLW1ldGVyOnNlY3JldA==");

        var formdata = new FormData();
        formdata.append("username", this.state.email);
        formdata.append("password", this.state.pwd);
        formdata.append("grant_type", "password");

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify({"username": this.state.email,
            "password": this.state.pwd, "grant_type": "password"}),
           // redirect: 'follow'
        };
        console.log(requestOptions)
        fetch("http://localhost:8080/oauth/token", requestOptions)
            .then(response => console.log(response))
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
