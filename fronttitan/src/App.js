import logo from './logo.svg';
import './App.css';
import React from 'react';
import $ from 'jquery';
import axios from 'axios'

class App extends React.Component {
  constructor(props){
    super(props);
    this.state={
      func1:"",
      func2:"",
      inputData:{},
      info:[]
    }
    this.func = this.func.bind(this);
    this.go = this.go.bind(this);
  }

  func(e){
    if(e.target.getAttribute('id')=="func1"){
      this.setState({func1:e.target.value})
    }
    else{
      this.setState({func2:e.target.value})
    }
  }

  go(e){
    e.preventDefault();
    let mode = true;
    let counter = 0;
    $(".mode").each((i,el)=>{
      if($(el).prop("checked")){
          // alert($(el).val())
      }
      else{
        counter++;
      }
  })
  if(counter==2){
    alert("choose mode");
  }
  
  else{
    axios.post("http://localhost:8080/calculator/calculate",{func1:"",func2:"",quantity:"",mode:true})
  }

  }

  render(){
    return (
      <div className="central">
        <form>
          <textarea onChange={this.func} id="func1" className="inputModal" type="text" required="true" placeholder="function 1" cols="18" rows="4" value={this.state.func1}></textarea>
          <textarea onChange={this.func} id="func2" className="inputModal" type="text" required="true" placeholder="function 2" cols="18" rows="4" value={this.state.func2}></textarea>        
          <input className="login" placeholder='quantity'></input>
          <div className='radioChoice'>
            <div>sort output?</div>
            <label>yes</label>
            <input className='mode' required type="radio" value={true} name="choise"></input>
            <label>no</label>
            <input className='mode' required type="radio" value={false} name="choise"></input>
          </div>
          <input type="submit" className='logInOrReg' onClick={this.go}/>
        </form>
        {
          this.state.info.map((ans)=>{
            return(
              <input>{ans.message}</input>
            )
          })
        }
      </div>
    );
  }
}

export default App;
