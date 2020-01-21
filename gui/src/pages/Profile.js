import React from "react";

export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            newPassword: '',
            repeatNewPassword: '',
            oldPassword: '',
            message:'',
        }
        this.handleNewPassword = this.handleNewPassword.bind(this);
        this.handleRepeatNewPassword  = this.handleRepeatNewPassword.bind(this);
        this.handleOldPassword  = this.handleOldPassword.bind(this);
    }
    changePassword(){

        fetch("https://localhost:8080/api/user/changePassword", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.currentUser.token
            },
            body: JSON.stringify({
                newPassword: this.state.newPassword,
                repeatNewPassword: this.state.repeatNewPassword,
                oldPassword: this.state.oldPassword,
            }),
        })
            .then(response => response.text())
            .then(response => {
                this.setState({
                        message: response,
                    }
                )})
            .catch(error => console.log('error', error));

    }

    handleNewPassword(event){
        this.setState({newPassword: event.target.value})
    }
    handleRepeatNewPassword(event){
        this.setState({repeatNewPassword: event.target.value})
    }
    handleOldPassword(event){
        this.setState({oldPassword: event.target.value})
    }

    render() {
        return <div>  <div> <h1> Witaj {this.props.currentUser.username} </h1><br/>  </div>
            <div>
                <h1>Zmien haslo</h1><br></br>
                <label htmlFor="changePassword"><b>Write New Password</b></label>
                 <input type="password" placeholder="Enter Password" name="psw"  value={this.state.newPassword} onChange={this.handleNewPassword} required />

                <label htmlFor="kwota"><b>Repeat New Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw1" value={this.state.repeatNewPassword} onChange={this.handleRepeatNewPassword} required/>

                <label htmlFor="title"><b>Old Password</b></label>
                <input type="password" placeholder="Enter Password" name="psw2" alue={this.state.oldPassword} onChange={this.handleOldPassword} required />

                <div>
                    <button type="submit" onClick={this.changePassword.bind(this)}>change Password</button>
                    <div><br></br> {this.state.message} </div>
                </div>



            </div>
        </div>
    }
}
