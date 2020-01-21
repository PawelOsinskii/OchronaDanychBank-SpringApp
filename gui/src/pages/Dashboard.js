import React from 'react';

export default class Dashboard extends React.Component {


    constructor(props) {
        super(props);
        this.state = {
            balance: 0,
            email: "",
            money: 0,
            message: "",
            title: "",
            history_received:"",
            history_sent:"",
        }
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangeMoney = this.handleChangeMoney.bind(this);
        this.handleChangeTitle = this.handleChangeTitle.bind(this);
        this.fetch_balance();
        this.fetch_history_sent();
        this.fetch_history_received();




    }
    handleChangeEmail(event){
        this.setState({email: event.target.value})
    }
    handleChangeMoney(event){
        this.setState({money: parseInt(event.target.value) || 0})
    }
    handleChangeTitle(event){
        this.setState({title: event.target.value})
    }
    fetch_balance(){
        fetch("https://localhost:8080/api/user/balance", {
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
fetch_history_sent(){
        fetch("https://localhost:8080/api/user/sendTransactions", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.currentUser.token
            }

        })
            .then(response => response.text())
            .then(history_sent => this.setState({history_sent}))
            .catch(error => console.log('error', error));
    }
    fetch_history_received(){
        fetch("https://localhost:8080/api/user/receiveTransactions", {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.currentUser.token
            }

        })
            .then(response => response.text())
            .then(history_received => this.setState({history_received}))
            .catch(error => console.log('error', error));
    }


    render() {
        return <div>  <div> <h1> Welcome {this.props.currentUser.username} </h1><br/> Your balance is {this.state.balance } coins </div>
            <div> <h1>Your history of Received Transactions: </h1><br/><pre>{this.state.history_received} </pre></div>
            <div> <h1>Your history of Sent Transactions: </h1><br/><pre>{this.state.history_sent} </pre></div>
        <div>
            <h1>Wykonaj przelew</h1><br></br>
            <label htmlFor="login"><b>Email Receiver</b></label>
            <input type="email" placeholder="Enter Email" name="email" value={this.state.email}
                   onChange={this.handleChangeEmail} required/>

            <label htmlFor="kwota"><b>title of transaction</b></label>
            <input  placeholder="write title" name="asd" value={this.state.title}
                   onChange={this.handleChangeTitle} required/>

            <label htmlFor="title"><b>money</b></label>
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
        fetch("https://localhost:8080/api/user/send", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + this.props.currentUser.token
            },
            body:JSON.stringify({
                email: this.state.email,
                money: this.state.money,
                title: this.state.title,
            })

        })
            .then(response => response.text())
            .then(response => {
                this.fetch_balance();
                this.fetch_history_received();
                this.fetch_history_sent();
                this.setState({
                    message: response,
                        }
            )})
            .catch(error => console.log('error', error));

    }

}




