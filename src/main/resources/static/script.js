const API_URL = 'http://localhost:8080/api';

function mostrarSecao(secaoId) {
    const secoes = ['cadastro', 'listagem', 'busca', 'atualizar', 'deletar'];
    secoes.forEach(secao => {
        const element = document.getElementById(secao);
        if (element) {
            element.style.display = 'none';
        }
    });
    const secaoElement = document.getElementById(secaoId);
    if (secaoElement) {
        secaoElement.style.display = 'block';
    }
}

function mostrarSecaoListagem() {
    mostrarSecao('listagem');
    carregarListagem();
}

function carregarListagem() {
    const tipo = document.getElementById('tipoListagem').value;
    let url = '';
    let titulo = '';
    
    const tiposMap = {
        'medicos': { url: `${API_URL}/medicos`, titulo: 'Médicos' },
        'dentistas': { url: `${API_URL}/dentistas`, titulo: 'Dentistas' },
        'psicologos': { url: `${API_URL}/psicologos`, titulo: 'Psicólogos' },
        'pacientes': { url: `${API_URL}/pacientes`, titulo: 'Pacientes' },
        'consultas': { url: `${API_URL}/consultas`, titulo: 'Consultas' },
        'planos': { url: `${API_URL}/planos`, titulo: 'Planos de Saúde' }
    };
    
    const config = tiposMap[tipo];
    if (!config) return;
    
    url = config.url;
    titulo = config.titulo;
    
    fetch(url)
        .then(response => response.json())
        .then(dados => {
            const container = document.getElementById('tabelaContainer');
            
            if (dados.length === 0) {
                container.innerHTML = '<p>Nenhum registro encontrado.</p>';
                return;
            }
            
            let tabela = `<h3>${titulo}</h3><table><thead><tr>`;
            
            if (tipo === 'medicos') {
                tabela += '<th>ID</th><th>Nome</th><th>Nº Registro</th><th>Especialidade</th><th>Telefone</th><th>Email</th>';
            } else if (tipo === 'dentistas') {
                tabela += '<th>ID</th><th>Nome</th><th>Nº Registro</th><th>Especialidade</th><th>Telefone</th><th>Email</th>';
            } else if (tipo === 'psicologos') {
                tabela += '<th>ID</th><th>Nome</th><th>Nº Registro</th><th>Especialidade</th><th>Telefone</th><th>Email</th>';
            } else if (tipo === 'pacientes') {
                tabela += '<th>ID</th><th>Nome</th><th>CPF</th><th>Data Nasc.</th><th>Telefone</th><th>Plano</th><th>Endereço</th>';
            } else if (tipo === 'consultas') {
                tabela += '<th>ID</th><th>Profissional</th><th>Paciente</th><th>Data/Hora</th><th>Status</th><th>Observação</th>';
            } else if (tipo === 'planos') {
                tabela += '<th>ID</th><th>Código</th><th>Nome</th><th>Operadora</th><th>Tipo</th><th>Desconto</th><th>Ativo</th>';
            }
            
            tabela += '</thead><tbody>';
            
            dados.forEach(item => {
                tabela += '<tr>';
                if (tipo === 'medicos') {
                    tabela += `<td>${item.id}</td><td>${item.nome}</td><td>${item.numeroRegistro}</td><td>${item.especialidade}</td><td>${item.telefone || '-'}</td><td>${item.email || '-'}</td>`;
                } else if (tipo === 'dentistas') {
                    tabela += `<td>${item.id}</td><td>${item.nome}</td><td>${item.numeroRegistro}</td><td>${item.especialidade}</td><td>${item.telefone || '-'}</td><td>${item.email || '-'}</td>`;
                } else if (tipo === 'psicologos') {
                    tabela += `<td>${item.id}</td><td>${item.nome}</td><td>${item.numeroRegistro}</td><td>${item.especialidade}</td><td>${item.telefone || '-'}</td><td>${item.email || '-'}</td>`;
                } else if (tipo === 'pacientes') {
                    tabela += `<td>${item.id}</td><td>${item.nome}</td><td>${item.cpf}</td><td>${item.dataNascimento}</td><td>${item.telefone || '-'}</td><td>${item.planoNome || 'Sem plano'}</td><td>${item.endereco || '-'}</td>`;
                } else if (tipo === 'consultas') {
                    tabela += `<td>${item.id}</td><td>${item.nomeProfissional}</td><td>${item.nomePaciente}</td><td>${new Date(item.dataHora).toLocaleString()}</td><td>${item.status}</td><td>${item.observacao || '-'}</td>`;
                } else if (tipo === 'planos') {
                    tabela += `<td>${item.id}</td><td>${item.codigo}</td><td>${item.nome}</td><td>${item.operadora || '-'}</td><td>${item.tipo || '-'}</td><td>${item.desconto}%</td><td>${item.ativo ? '✅ Ativo' : '❌ Inativo'}</td>`;
                }
                tabela += '</tr>';
            });
            
            tabela += '</tbody></table>';
            container.innerHTML = tabela;
        })
        .catch(error => {
            console.error('Erro:', error);
            document.getElementById('tabelaContainer').innerHTML = '<p class="erro">Erro ao carregar dados</p>';
        });
}

function mostrarFormCadastro() {
    const tipo = document.getElementById('tipoCadastro').value;
    const forms = ['formMedico', 'formDentista', 'formPsicologo', 'formPaciente', 'formConsulta', 'formPlano'];
    forms.forEach(form => {
        const element = document.getElementById(form);
        if (element) {
            element.style.display = 'none';
        }
    });
    
    const formToShow = document.getElementById(`form${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`);
    if (formToShow) {
        formToShow.style.display = 'block';
        if (tipo === 'paciente') {
            carregarPlanosSelect();
        } else if (tipo === 'consulta') {
            carregarProfissionaisSelect();
            carregarPacientesSelect();
        }
    }
}

function mostrarCampoBusca() {
    const tipo = document.getElementById('tipoBusca').value;
    const campoBusca = document.getElementById('campoBusca');
    campoBusca.innerHTML = `
        <input type="text" id="buscaNome" placeholder="Nome para buscar">
        <button onclick="buscarPorNome()">Buscar</button>
    `;
}

function carregarPlanosSelect() {
    fetch(`${API_URL}/planos/ativos`)
        .then(response => response.json())
        .then(planos => {
            const selectPlano = document.getElementById('pacientePlanoId');
            if (selectPlano) {
                selectPlano.innerHTML = '<option value="">Selecione um plano de saúde</option>';
                planos.forEach(plano => {
                    selectPlano.innerHTML += `<option value="${plano.id}">${plano.nome} - ${plano.desconto}% desconto</option>`;
                });
            }
        })
        .catch(error => console.error('Erro ao carregar planos:', error));
}

function carregarProfissionaisSelect() {
    Promise.all([
        fetch(`${API_URL}/medicos`).then(r => r.json()),
        fetch(`${API_URL}/dentistas`).then(r => r.json()),
        fetch(`${API_URL}/psicologos`).then(r => r.json())
    ]).then(([medicos, dentistas, psicologos]) => {
        const select = document.getElementById('consultaProfissionalId');
        select.innerHTML = '<option value="">Selecione um profissional</option>';
        
        medicos.forEach(p => select.innerHTML += `<option value="${p.id}">👨‍⚕️ Médico: ${p.nome} - ${p.especialidade}</option>`);
        dentistas.forEach(p => select.innerHTML += `<option value="${p.id}">🦷 Dentista: ${p.nome} - ${p.especialidade}</option>`);
        psicologos.forEach(p => select.innerHTML += `<option value="${p.id}">🧠 Psicólogo: ${p.nome} - ${p.especialidade}</option>`);
    }).catch(error => console.error('Erro ao carregar profissionais:', error));
}

function carregarPacientesSelect() {
    fetch(`${API_URL}/pacientes`)
        .then(response => response.json())
        .then(pacientes => {
            const select = document.getElementById('consultaPacienteId');
            select.innerHTML = '<option value="">Selecione um paciente</option>';
            pacientes.forEach(p => {
                select.innerHTML += `<option value="${p.id}">${p.nome} - ${p.cpf}</option>`;
            });
        })
        .catch(error => console.error('Erro ao carregar pacientes:', error));
}

function cadastrarMedico() {
    const medico = {
        nome: document.getElementById('medicoNome').value,
        numeroRegistro: document.getElementById('medicoNumeroRegistro').value,
        especialidade: document.getElementById('medicoEspecialidade').value,
        telefone: document.getElementById('medicoTelefone').value,
        email: document.getElementById('medicoEmail').value
    };
    
    fetch(`${API_URL}/medicos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(medico)
    })
    .then(response => {
        if (response.ok) {
            alert('Médico cadastrado com sucesso!');
            limparCamposCadastro();
        } else {
            response.json().then(data => alert('Erro: ' + JSON.stringify(data)));
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    });
}

function cadastrarDentista() {
    const dentista = {
        nome: document.getElementById('dentistaNome').value,
        numeroRegistro: document.getElementById('dentistaNumeroRegistro').value,
        especialidade: document.getElementById('dentistaEspecialidade').value,
        telefone: document.getElementById('dentistaTelefone').value,
        email: document.getElementById('dentistaEmail').value
    };
    
    fetch(`${API_URL}/dentistas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dentista)
    })
    .then(response => {
        if (response.ok) {
            alert('Dentista cadastrado com sucesso!');
            limparCamposCadastro();
        } else {
            response.json().then(data => alert('Erro: ' + JSON.stringify(data)));
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    });
}

function cadastrarPsicologo() {
    const psicologo = {
        nome: document.getElementById('psicologoNome').value,
        numeroRegistro: document.getElementById('psicologoNumeroRegistro').value,
        especialidade: document.getElementById('psicologoEspecialidade').value,
        telefone: document.getElementById('psicologoTelefone').value,
        email: document.getElementById('psicologoEmail').value
    };
    
    fetch(`${API_URL}/psicologos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(psicologo)
    })
    .then(response => {
        if (response.ok) {
            alert('Psicólogo cadastrado com sucesso!');
            limparCamposCadastro();
        } else {
            response.json().then(data => alert('Erro: ' + JSON.stringify(data)));
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    });
}

function cadastrarPaciente() {
    const paciente = {
        nome: document.getElementById('pacienteNome').value,
        cpf: document.getElementById('pacienteCPF').value,
        dataNascimento: document.getElementById('pacienteDataNascimento').value,
        telefone: document.getElementById('pacienteTelefone').value,
        endereco: document.getElementById('pacienteEndereco').value,
        planoId: document.getElementById('pacientePlanoId').value ? parseInt(document.getElementById('pacientePlanoId').value) : null
    };
    
    fetch(`${API_URL}/pacientes`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(paciente)
    })
    .then(response => {
        if (response.ok) {
            alert('Paciente cadastrado com sucesso!');
            limparCamposCadastro();
        } else {
            response.json().then(data => alert('Erro: ' + JSON.stringify(data)));
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    });
}

function cadastrarConsulta() {
    const consulta = {
        profissionalId: parseInt(document.getElementById('consultaProfissionalId').value),
        pacienteId: parseInt(document.getElementById('consultaPacienteId').value),
        dataHora: document.getElementById('consultaDataHora').value,
        observacao: document.getElementById('consultaObservacao').value,
        status: document.getElementById('consultaStatus').value
    };
    
    fetch(`${API_URL}/consultas`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(consulta)
    })
    .then(response => {
        if (response.ok) {
            alert('Consulta agendada com sucesso!');
            limparCamposCadastro();
        } else {
            response.json().then(data => alert('Erro: ' + JSON.stringify(data)));
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    });
}

function cadastrarPlano() {
    const plano = {
        codigo: document.getElementById('planoCodigo').value,
        nome: document.getElementById('planoNome').value,
        operadora: document.getElementById('planoOperadora').value,
        tipo: document.getElementById('planoTipo').value,
        desconto: parseFloat(document.getElementById('planoDesconto').value),
        ativo: document.getElementById('planoAtivo').checked,
        observacoes: document.getElementById('planoObservacoes').value
    };
    
    fetch(`${API_URL}/planos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(plano)
    })
    .then(response => {
        if (response.ok) {
            alert('Plano cadastrado com sucesso!');
            limparCamposCadastro();
        } else {
            response.json().then(data => alert('Erro: ' + JSON.stringify(data)));
        }
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao conectar com o servidor');
    });
}

function buscarPorNome() {
    const tipo = document.getElementById('tipoBusca').value;
    const nome = document.getElementById('buscaNome').value;
    
    if (!nome) {
        alert('Digite um nome para buscar');
        return;
    }
    
    let url = '';
    if (tipo === 'medico') {
        url = `${API_URL}/medicos/buscar?nome=${nome}`;
    } else if (tipo === 'dentista') {
        url = `${API_URL}/dentistas/buscar?nome=${nome}`;
    } else if (tipo === 'psicologo') {
        url = `${API_URL}/psicologos/buscar?nome=${nome}`;
    } else if (tipo === 'paciente') {
        url = `${API_URL}/pacientes/buscar?nome=${nome}`;
    } else if (tipo === 'plano') {
        url = `${API_URL}/planos/buscar?nome=${nome}`;
    }
    
    fetch(url)
        .then(response => response.json())
        .then(dados => {
            const resultadoDiv = document.getElementById('resultadoBusca');
            
            if (dados.length === 0) {
                resultadoDiv.innerHTML = '<p>Nenhum resultado encontrado.</p>';
                return;
            }
            
            let html = '<h3>Resultados:</h3><table><thead><tr>';
            
            if (tipo === 'medico' || tipo === 'dentista' || tipo === 'psicologo') {
                html += '<th>ID</th><th>Nome</th><th>Nº Registro</th><th>Especialidade</th><th>Telefone</th><th>Email</th>';
            } else if (tipo === 'paciente') {
                html += '<th>ID</th><th>Nome</th><th>CPF</th><th>Data Nasc.</th><th>Telefone</th><th>Plano</th>';
            } else if (tipo === 'plano') {
                html += '<th>ID</th><th>Código</th><th>Nome</th><th>Operadora</th><th>Desconto</th><th>Ativo</th>';
            }
            
            html += '</thead><tbody>';
            
            dados.forEach(item => {
                html += '<tr>';
                if (tipo === 'medico' || tipo === 'dentista' || tipo === 'psicologo') {
                    html += `<td>${item.id}</td><td>${item.nome}</td><td>${item.numeroRegistro}</td><td>${item.especialidade}</td><td>${item.telefone || '-'}</td><td>${item.email || '-'}</td>`;
                } else if (tipo === 'paciente') {
                    html += `<td>${item.id}</td><td>${item.nome}</td><td>${item.cpf}</td><td>${item.dataNascimento}</td><td>${item.telefone || '-'}</td><td>${item.planoNome || 'Sem plano'}</td>`;
                } else if (tipo === 'plano') {
                    html += `<td>${item.id}</td><td>${item.codigo}</td><td>${item.nome}</td><td>${item.operadora || '-'}</td><td>${item.desconto}%</td><td>${item.ativo ? '✅ Ativo' : '❌ Inativo'}</td>`;
                }
                html += '</tr>';
            });
            
            html += '</tbody></table>';
            resultadoDiv.innerHTML = html;
        })
        .catch(error => {
            console.error('Erro:', error);
            document.getElementById('resultadoBusca').innerHTML = '<p class="erro">Erro na busca</p>';
        });
}

function mostrarFormAtualizar() {
    const tipo = document.getElementById('tipoAtualizar').value;
    const forms = ['formAtualizarMedico', 'formAtualizarDentista', 'formAtualizarPsicologo', 'formAtualizarPaciente', 'formAtualizarConsulta', 'formAtualizarPlano'];
    forms.forEach(form => {
        const element = document.getElementById(form);
        if (element) {
            element.style.display = 'none';
        }
    });
    
    const formToShow = document.getElementById(`formAtualizar${tipo.charAt(0).toUpperCase() + tipo.slice(1)}`);
    if (formToShow) {
        formToShow.style.display = 'block';
    }
}

function carregarMedico() {
    const id = document.getElementById('atualizarMedicoId').value;
    if (!id) { alert('Digite o ID do médico'); return; }
    
    fetch(`${API_URL}/medicos/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Médico não encontrado');
            return response.json();
        })
        .then(medico => {
            document.getElementById('dadosMedico').innerHTML = `
                <input type="text" id="updateMedicoNome" value="${medico.nome}" placeholder="Nome">
                <input type="text" id="updateMedicoNumeroRegistro" value="${medico.numeroRegistro}" placeholder="Número Registro">
                <input type="text" id="updateMedicoEspecialidade" value="${medico.especialidade}" placeholder="Especialidade">
                <input type="tel" id="updateMedicoTelefone" value="${medico.telefone || ''}" placeholder="Telefone">
                <input type="email" id="updateMedicoEmail" value="${medico.email || ''}" placeholder="Email">
                <button onclick="atualizarMedico(${medico.id})">Atualizar Médico</button>
            `;
        })
        .catch(error => alert('Erro ao carregar médico'));
}

function atualizarMedico(id) {
    const medico = {
        nome: document.getElementById('updateMedicoNome').value,
        numeroRegistro: document.getElementById('updateMedicoNumeroRegistro').value,
        especialidade: document.getElementById('updateMedicoEspecialidade').value,
        telefone: document.getElementById('updateMedicoTelefone').value,
        email: document.getElementById('updateMedicoEmail').value
    };
    
    fetch(`${API_URL}/medicos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(medico)
    })
    .then(response => {
        if (response.ok) {
            alert('Médico atualizado com sucesso!');
            document.getElementById('atualizarMedicoId').value = '';
            document.getElementById('dadosMedico').innerHTML = '';
        } else {
            alert('Erro ao atualizar médico');
        }
    })
    .catch(error => alert('Erro ao conectar com o servidor'));
}

function carregarDentista() {
    const id = document.getElementById('atualizarDentistaId').value;
    if (!id) { alert('Digite o ID do dentista'); return; }
    
    fetch(`${API_URL}/dentistas/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Dentista não encontrado');
            return response.json();
        })
        .then(dentista => {
            document.getElementById('dadosDentista').innerHTML = `
                <input type="text" id="updateDentistaNome" value="${dentista.nome}" placeholder="Nome">
                <input type="text" id="updateDentistaNumeroRegistro" value="${dentista.numeroRegistro}" placeholder="Número Registro">
                <input type="text" id="updateDentistaEspecialidade" value="${dentista.especialidade}" placeholder="Especialidade">
                <input type="tel" id="updateDentistaTelefone" value="${dentista.telefone || ''}" placeholder="Telefone">
                <input type="email" id="updateDentistaEmail" value="${dentista.email || ''}" placeholder="Email">
                <button onclick="atualizarDentista(${dentista.id})">Atualizar Dentista</button>
            `;
        })
        .catch(error => alert('Erro ao carregar dentista'));
}

function atualizarDentista(id) {
    const dentista = {
        nome: document.getElementById('updateDentistaNome').value,
        numeroRegistro: document.getElementById('updateDentistaNumeroRegistro').value,
        especialidade: document.getElementById('updateDentistaEspecialidade').value,
        telefone: document.getElementById('updateDentistaTelefone').value,
        email: document.getElementById('updateDentistaEmail').value
    };
    
    fetch(`${API_URL}/dentistas/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dentista)
    })
    .then(response => {
        if (response.ok) {
            alert('Dentista atualizado com sucesso!');
            document.getElementById('atualizarDentistaId').value = '';
            document.getElementById('dadosDentista').innerHTML = '';
        } else {
            alert('Erro ao atualizar dentista');
        }
    })
    .catch(error => alert('Erro ao conectar com o servidor'));
}

function carregarPsicologo() {
    const id = document.getElementById('atualizarPsicologoId').value;
    if (!id) { alert('Digite o ID do psicólogo'); return; }
    
    fetch(`${API_URL}/psicologos/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Psicólogo não encontrado');
            return response.json();
        })
        .then(psicologo => {
            document.getElementById('dadosPsicologo').innerHTML = `
                <input type="text" id="updatePsicologoNome" value="${psicologo.nome}" placeholder="Nome">
                <input type="text" id="updatePsicologoNumeroRegistro" value="${psicologo.numeroRegistro}" placeholder="Número Registro">
                <input type="text" id="updatePsicologoEspecialidade" value="${psicologo.especialidade}" placeholder="Especialidade">
                <input type="tel" id="updatePsicologoTelefone" value="${psicologo.telefone || ''}" placeholder="Telefone">
                <input type="email" id="updatePsicologoEmail" value="${psicologo.email || ''}" placeholder="Email">
                <button onclick="atualizarPsicologo(${psicologo.id})">Atualizar Psicólogo</button>
            `;
        })
        .catch(error => alert('Erro ao carregar psicólogo'));
}

function atualizarPsicologo(id) {
    const psicologo = {
        nome: document.getElementById('updatePsicologoNome').value,
        numeroRegistro: document.getElementById('updatePsicologoNumeroRegistro').value,
        especialidade: document.getElementById('updatePsicologoEspecialidade').value,
        telefone: document.getElementById('updatePsicologoTelefone').value,
        email: document.getElementById('updatePsicologoEmail').value
    };
    
    fetch(`${API_URL}/psicologos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(psicologo)
    })
    .then(response => {
        if (response.ok) {
            alert('Psicólogo atualizado com sucesso!');
            document.getElementById('atualizarPsicologoId').value = '';
            document.getElementById('dadosPsicologo').innerHTML = '';
        } else {
            alert('Erro ao atualizar psicólogo');
        }
    })
    .catch(error => alert('Erro ao conectar com o servidor'));
}

function carregarPaciente() {
    const id = document.getElementById('atualizarPacienteId').value;
    if (!id) { alert('Digite o ID do paciente'); return; }
    
    fetch(`${API_URL}/pacientes/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Paciente não encontrado');
            return response.json();
        })
        .then(paciente => {
            carregarPlanosSelectUpdate(paciente);
        })
        .catch(error => alert('Erro ao carregar paciente'));
}

function carregarPlanosSelectUpdate(paciente) {
    fetch(`${API_URL}/planos/ativos`)
        .then(response => response.json())
        .then(planos => {
            let options = '<option value="">Sem plano</option>';
            planos.forEach(plano => {
                const selected = paciente.planoId === plano.id ? 'selected' : '';
                options += `<option value="${plano.id}" ${selected}>${plano.nome} - ${plano.desconto}% desconto</option>`;
            });
            
            document.getElementById('dadosPaciente').innerHTML = `
                <input type="text" id="updatePacienteNome" value="${paciente.nome}" placeholder="Nome">
                <input type="text" id="updatePacienteCPF" value="${paciente.cpf}" placeholder="CPF">
                <input type="date" id="updatePacienteDataNascimento" value="${paciente.dataNascimento}">
                <input type="tel" id="updatePacienteTelefone" value="${paciente.telefone || ''}" placeholder="Telefone">
                <input type="text" id="updatePacienteEndereco" value="${paciente.endereco || ''}" placeholder="Endereço">
                <select id="updatePacientePlanoId">${options}</select>
                <button onclick="atualizarPaciente(${paciente.id})">Atualizar Paciente</button>
            `;
        })
        .catch(error => console.error('Erro ao carregar planos:', error));
}

function atualizarPaciente(id) {
    const paciente = {
        nome: document.getElementById('updatePacienteNome').value,
        cpf: document.getElementById('updatePacienteCPF').value,
        dataNascimento: document.getElementById('updatePacienteDataNascimento').value,
        telefone: document.getElementById('updatePacienteTelefone').value,
        endereco: document.getElementById('updatePacienteEndereco').value,
        planoId: document.getElementById('updatePacientePlanoId').value ? parseInt(document.getElementById('updatePacientePlanoId').value) : null
    };
    
    fetch(`${API_URL}/pacientes/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(paciente)
    })
    .then(response => {
        if (response.ok) {
            alert('Paciente atualizado com sucesso!');
            document.getElementById('atualizarPacienteId').value = '';
            document.getElementById('dadosPaciente').innerHTML = '';
        } else {
            alert('Erro ao atualizar paciente');
        }
    })
    .catch(error => alert('Erro ao conectar com o servidor'));
}

function carregarConsulta() {
    const id = document.getElementById('atualizarConsultaId').value;
    if (!id) { alert('Digite o ID da consulta'); return; }
    
    fetch(`${API_URL}/consultas/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Consulta não encontrada');
            return response.json();
        })
        .then(consulta => {
            const dataHoraFormatada = consulta.dataHora.replace(' ', 'T').substring(0, 16);
            document.getElementById('dadosConsulta').innerHTML = `
                <input type="datetime-local" id="updateConsultaDataHora" value="${dataHoraFormatada}">
                <textarea id="updateConsultaObservacao" placeholder="Observações">${consulta.observacao || ''}</textarea>
                <select id="updateConsultaStatus">
                    <option value="AGENDADA" ${consulta.status === 'AGENDADA' ? 'selected' : ''}>Agendada</option>
                    <option value="REALIZADA" ${consulta.status === 'REALIZADA' ? 'selected' : ''}>Realizada</option>
                    <option value="CANCELADA" ${consulta.status === 'CANCELADA' ? 'selected' : ''}>Cancelada</option>
                </select>
                <button onclick="atualizarConsulta(${consulta.id})">Atualizar Consulta</button>
            `;
        })
        .catch(error => alert('Erro ao carregar consulta'));
}

function atualizarConsulta(id) {
    const consulta = {
        dataHora: document.getElementById('updateConsultaDataHora').value,
        observacao: document.getElementById('updateConsultaObservacao').value,
        status: document.getElementById('updateConsultaStatus').value
    };
    
    fetch(`${API_URL}/consultas/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(consulta)
    })
    .then(response => {
        if (response.ok) {
            alert('Consulta atualizada com sucesso!');
            document.getElementById('atualizarConsultaId').value = '';
            document.getElementById('dadosConsulta').innerHTML = '';
        } else {
            alert('Erro ao atualizar consulta');
        }
    })
    .catch(error => alert('Erro ao conectar com o servidor'));
}

function carregarPlano() {
    const id = document.getElementById('atualizarPlanoId').value;
    if (!id) { alert('Digite o ID do plano'); return; }
    
    fetch(`${API_URL}/planos/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Plano não encontrado');
            return response.json();
        })
        .then(plano => {
            document.getElementById('dadosPlano').innerHTML = `
                <input type="text" id="updatePlanoCodigo" value="${plano.codigo}" placeholder="Código">
                <input type="text" id="updatePlanoNome" value="${plano.nome}" placeholder="Nome">
                <input type="text" id="updatePlanoOperadora" value="${plano.operadora || ''}" placeholder="Operadora">
                <input type="text" id="updatePlanoTipo" value="${plano.tipo || ''}" placeholder="Tipo">
                <input type="number" id="updatePlanoDesconto" step="0.01" value="${plano.desconto}" placeholder="Desconto (%)">
                <label>
                    <input type="checkbox" id="updatePlanoAtivo" ${plano.ativo ? 'checked' : ''}> Plano ativo
                </label>
                <textarea id="updatePlanoObservacoes" placeholder="Observações">${plano.observacoes || ''}</textarea>
                <button onclick="atualizarPlano(${plano.id})">Atualizar Plano</button>
            `;
        })
        .catch(error => alert('Erro ao carregar plano'));
}

function atualizarPlano(id) {
    const plano = {
        codigo: document.getElementById('updatePlanoCodigo').value,
        nome: document.getElementById('updatePlanoNome').value,
        operadora: document.getElementById('updatePlanoOperadora').value,
        tipo: document.getElementById('updatePlanoTipo').value,
        desconto: parseFloat(document.getElementById('updatePlanoDesconto').value),
        ativo: document.getElementById('updatePlanoAtivo').checked,
        observacoes: document.getElementById('updatePlanoObservacoes').value
    };
    
    fetch(`${API_URL}/planos/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(plano)
    })
    .then(response => {
        if (response.ok) {
            alert('Plano atualizado com sucesso!');
            document.getElementById('atualizarPlanoId').value = '';
            document.getElementById('dadosPlano').innerHTML = '';
        } else {
            alert('Erro ao atualizar plano');
        }
    })
    .catch(error => alert('Erro ao conectar com o servidor'));
}

function deletarRegistro() {
    const tipo = document.getElementById('tipoDeletar').value;
    const id = document.getElementById('deletarId').value;
    
    if (!id) {
        alert('Digite o ID do registro');
        return;
    }
    
    if (!confirm(`Tem certeza que deseja deletar este ${tipo}?`)) {
        return;
    }
    
    let url = '';
    if (tipo === 'medico') url = `${API_URL}/medicos/${id}`;
    else if (tipo === 'dentista') url = `${API_URL}/dentistas/${id}`;
    else if (tipo === 'psicologo') url = `${API_URL}/psicologos/${id}`;
    else if (tipo === 'paciente') url = `${API_URL}/pacientes/${id}`;
    else if (tipo === 'consulta') url = `${API_URL}/consultas/${id}`;
    else if (tipo === 'plano') url = `${API_URL}/planos/${id}`;
    
    fetch(url, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                const msgDiv = document.getElementById('mensagemDeletar');
                msgDiv.innerHTML = '<div class="mensagem sucesso">Registro deletado com sucesso!</div>';
                document.getElementById('deletarId').value = '';
                setTimeout(() => msgDiv.innerHTML = '', 3000);
            } else {
                document.getElementById('mensagemDeletar').innerHTML = '<div class="mensagem erro">Erro ao deletar registro</div>';
            }
        })
        .catch(error => {
            document.getElementById('mensagemDeletar').innerHTML = '<div class="mensagem erro">Erro ao conectar com o servidor</div>';
        });
}

function limparCamposCadastro() {
    const inputs = document.querySelectorAll('#cadastro input, #cadastro textarea, #cadastro select');
    inputs.forEach(input => {
        if (input.type !== 'checkbox') {
            input.value = '';
        } else {
            input.checked = true;
        }
    });
}

mostrarSecao('cadastro');
mostrarFormCadastro();