import React from 'react';

export default class Dashboard extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            balance: 0,
            email: "",
            money: 0,
            message: "",
        }
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangeMoney = this.handleChangeMoney.bind(this);
        this.fetch_balance();




    }
    handleChangeEmail(event){
        this.setState({email: event.target.value})
    }
    handleChangeMoney(event){
        this.setState({money: parseInt(event.target.value) || 0})
    }
    fetch_balance(){
        fetch("http://localhost:8080/api/user/balance", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.currentUser.token
            }

        })
            .then(response => response.text())
            .then(balance => this.setState({balance}))
            .catch(error => console.log('error', error));
    }


    render() {
        return <div>  <div> <h1> Witaj {this.props.currentUser.username} </h1><br/> Your balance is {this.state.balance } coins </div>
        <div>
            <h1>Wykonaj przelew</h1><br></br>
            <label htmlFor="login"><b>Email odbiorcy</b></label>
            <input type="email" placeholder="Enter Email" name="email" value={this.state.email}
                   onChange={this.handleChangeEmail} required/>
            <label htmlFor="kwota"><b>Kwota Przelewu</b></label>
            <input type="number" pattern="[0-9\/]*" placeholder="Enter money" name="money" value={this.state.money}
                   onChange={this.handleChangeMoney} required/>
            <div>
                <button type="submit" onClick={this.sendMoney.bind(this)}>send Money</button>
                <div><br></br> {this.state.message} </div>
            </div>


        </div>
        </div>
    }
    sendMoney(){
        fetch("http://localhost:8080/api/user/send", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.currentUser.token
            },
            body:JSON.stringify({
                email: this.state.email,
                money: this.state.money,
            })

        })
            .then(response => response.text())
            .then(response => {
                this.fetch_balance();
                this.setState({
                    message: response,
                        }
            )})
            .catch(error => console.log('error', error));

    }

}




