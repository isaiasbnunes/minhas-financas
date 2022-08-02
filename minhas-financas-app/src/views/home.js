import axios from "axios";
import React from "react";

class Home extends React.Component{

    state = {
        saldo: 0
    }

    componentDidMount(){
        axios.get('http://localhost:8080/api/usuarios/1/saldo')
        .then(r => {
            this.setState({saldo: r.data})
        }).catch(error=>{
            console.log(error.r)
        })
    }

    render(){
        return(

        <div className="jumbotron">
            <h1 className="display-3">Bem vindo!</h1>
            <p className="lead">Esse é seu sistema de finanças.</p>
            <p className="lead">Seu saldo para o mês atual é de R$ {this.state.saldo}</p>
            <hr className="my-4" />
            <p>E essa é sua área administrativa, utilize um dos menus ou botões abaixo para navegar pelo sistema.</p>
            <p className="lead">
                <a className="btn btn-primary btn-lg"
                    href="/cadastro-usuarios" role="button">
                        <i className="fa fa-users"></i>
                        Cadastrar Usuário</a>
                <a className="btn btn-danger btn-lg"
                    href="https://bootswatch.com/flatly/#" role="button">
                        <i className="fa fa-users"></i>
                        Cadastrar Lançamento</a>
            </p>
        </div>

        )
    }

} export default Home