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
      info:[] //{answer:"Test data",msg:"below"}, {answer:"<0>,<1>,<0.0>,<3330003>",msg:"Success"},{answer:"<0>,<2>,<0.0>,<3330003>",msg:"Success"}
    };
    this.func = this.func.bind(this);
    this.go = this.go.bind(this);
    this.go2 = this.go2.bind(this);
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
    // alert($("#quantity").val());
    let mode = true;
    let counter = 0;
    $(".mode").each((i,el)=>{
      if($(el).prop("checked")){
          // alert($(el).val())
          mode = $(el).val();
      }
      else{
        counter++;
      }
  })
  if(counter==2){
    alert("choose mode");
  }
  else{
    let i =0;
    let quantity = $("#quantity").val();
    if(!isNaN(parseInt(quantity))){
          axios.post("http://localhost:8080/calculator/calculate",{func1:this.state.func1,func2:this.state.func2,quantity:quantity,mode:mode}).then(
            (response)=>{
                console.log(response.data);
                this.setState({info:response.data})
              // }
            }
          )
      }
      else{
        alert("unparsable quantity");
      }
    
  
    } 

  }


  go2(e){
    e.preventDefault();
    // var url = URL('http://localhost:8080/calculator/3');
    // var url = new URL("https://a.com/method"),
    fetch("http://localhost:8080/calculator/3",)
    .then(res => {
      const reader = res.body.getReader();
      return reader.read().then((done,value) => {
        if(done){
          return null;
        }
        console.log(value);
      });
    })
    // .then(data => console.log(data));
    
  }

  render(){
    return (
      <div className="central">
        <form>
          <h3>note that</h3><h3> functions MUST be called "func"</h3><h3> And MUST have ONLY ONE int parameter</h3>
          <textarea onChange={this.func} id="func1" className="inputModal" type="text" required="true" placeholder="function 1" cols="18" rows="4" value={this.state.func1}></textarea>
          <textarea onChange={this.func} id="func2" className="inputModal" type="text" required="true" placeholder="function 2" cols="18" rows="4" value={this.state.func2}></textarea>        
          <input id='quantity' className="login" placeholder='quantity'></input>
          <div className='radioChoice'>
            <div>order output?</div>
            <label>yes</label>
            <input className='mode' required type="radio" value={true} name="choise"></input>
            <label>no</label>
            <input className='mode' required type="radio" value={false} name="choise"></input>
          </div>
          <input type="submit" className='logInOrReg' onClick={this.go}/>
        </form>
        <div>
            <table>
              <thead>
                <tr>
                  <th>
                    answer
                  </th>
                  <th>
                    msg
                  </th>
                </tr>
              </thead>
              <tbody>
              {
          this.state.info.map((resp)=>{
            // alert(resp.msg)
            return(
             <tr>
               <td>
                {resp.answer}
               </td>
               <td>
                 {resp.msg}
               </td>
             </tr>
            )
          })
        }
              </tbody>
            </table>
          </div>
        
      </div>
    );
  }
}

export default App;



function func(j){
  var r = -65000;
  var t =0;
  while(r<65000){
  r++;
  t=-65000;
  while(t<100){
  t++;}
  }
  return j;
  }