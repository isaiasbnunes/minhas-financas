import React from "react";
import Card from "../components/card";
import FormGroup from "../components/form-group";
import {useNavigate} from 'react-router-dom';
import axios from "axios";

class Login extends React.Component{

    
    state = {
        senha : '',
        email: '',
        mensagem: '',
        

    }
    entrar = async () =>{
         await axios.post('http://localhost:8080/api/usuarios/autenticar',
        {
            email : this.state.email,
            senha: this.state.senha
        }).then(response =>{
            localStorage.setItem('_usuario_logado',
             JSON.stringify(response.data))
             
       
             useNavigate('<Home />');
             console.log(">>> logou")
             
        }).catch(erro => {
            this.setState({mensagem : erro.response.data})
        })
    }

    render(){ 
        return (
    
            <div className="row">
                <div className="col-md-6" style={{position : 'relative', left: '300px'}} >
                    <div className="bs-docs-section">
                        <Card title="Login">
                            <div className="row">
                                <span>{this.state.mensagem}</span>
                            </div>
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="bs-component">
                                        <fieldset>

                                            <FormGroup label="Email: *" 
                                                 htmlFor="inputEmail">
                                                    <input type="email"
                                                       
                                                       defaultValue={this.state.email}
                                                       onChange={e =>this.setState({email: e.target.value})}
                                                       className="form-control"
                                                       id="inputEmail"
                                                       aria-describedby="emailHelp"
                                                       placeholder="Digite seu email" />
                                                 </FormGroup>

                                                 <FormGroup label="Senha: *" 
                                                 htmlFor="inputPassword">
                                                    <input type="password"
                                                        defaultValue={this.state.senha}
                                                        onChange={e=>this.setState({senha: e.target.value})}
                                                       className="form-control"
                                                       id="inputPassword"
                                                       aria-describedby="emailHelp"
                                                       placeholder="Digite sua senha" />
                                                 </FormGroup>
                                                 <br />
                                                 <button onClick={this.entrar} className="btn btn-success">Entrar</button>
                                                 <a href="/cadastro-usuarios" className="btn btn-danger btn-md" role="button">Cadastrar</a>

                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </div>
                </div>
            </div>
        
    )}

} export default Login
