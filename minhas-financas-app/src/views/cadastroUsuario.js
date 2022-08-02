import React from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";

class CadastroUsuario extends React.Component{

    state ={
        nome : '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    cadastrar = () =>{
        console.log(this.state)
    }

    render(){
        return(
            
                <Card title="Cadastro de Usuário">
                    <div className='row'>
                        <div className='col-lg-12'>
                            <div className='bd-component'>
                                <FormGroup label="Nome: *" htmlForm="inputNome">
                                    <input type="text" id="inputNome"
                                            className="form-control" name="nome"
                                            onChange={e => this.setState({nome: e.target.value})} />
                                </FormGroup>

                                <FormGroup label="Email: *" htmlForm="inputEmail">
                                    <input type="email" id="inputEmail"
                                            className="form-control" name="email"
                                            onChange={e => this.setState({email: e.target.value})} />
                                </FormGroup>
                                <FormGroup label="Senha: *" htmlForm="inputSenha">
                                    <input type="password" id="inputSenha"
                                            className="form-control" name="senha"
                                            onChange={e => this.setState({senha: e.target.value})} />
                                </FormGroup>
                                <FormGroup label="Repita a senha: *" htmlForm="inputSenhaRepeticao">
                                    <input type="password" id="inputSenhaRepeticao"
                                            className="form-control" name="senhaRepeticao"
                                            onChange={e => this.setState({senhaRepeticao: e.target.value})} />
                                </FormGroup>
                                <button onClick={this.cadastrar} type="button" className="btn btn-success">Salvar</button>
                                <a href="/login" className="btn btn-danger btn-md" role="button">Cancelar</a>
                            </div>
                        </div>
                    </div>
                </Card>
        
        )
    }

} export default CadastroUsuario