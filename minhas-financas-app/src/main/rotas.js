import React from "react";
import Login from "../views/login";
import CadastroUsuario from "../views/cadastroUsuario";

import {BrowserRouter, Route, Routes} from 'react-router-dom'
import Home from "../views/home";

function Rotas(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/cadastro-usuarios" element={<CadastroUsuario />} />
                <Route path="/home" element={<Home />} />
                <Route path="/" element={<Home />} />
            </Routes>
        </BrowserRouter>
    )
} export default Rotas