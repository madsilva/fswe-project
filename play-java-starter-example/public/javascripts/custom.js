function sendDeleteRequest(url, rUrl){
  $.ajax({
      url: url,
      method: "DELETE",
      success: function(){
        window.location = rUrl;
      },
      error: function(){
        window.location.reload();
      }
  });
}

function hideblock(value){
    document.write("IN THE HIDE PRECINCT");
    if (value == "StateElection"){
        var precinct = document.getElementById("precinctid");
        precinct.style.display === "none";
    }
}


function hideprecinct(){
    document.write("IN THE HIDE PRECINCT FUNCTION")
    var state = document.getElementById("stateName");
    state.style.display === "none"

    var precinct = document.getElementById("precinctid");
    precinct.style.display === "block"

}

function hidestateandprecinct(){
    document.write("IN THE HIDE PRECINCT FUNCTION 2")

    var state = document.getElementById("stateName");
    state.style.display === "none"

    var precinct = document.getElementById("precinctid");
    precinct.style.display === "none"
}

function showstateandprecinct(){
    document.write("IN THE HIDE PRECINCT FUNCTION 3")

    var state = document.getElementById("stateName");
    state.style.display === "block"

    var precinct = document.getElementById("precinctid");
    precinct.style.display === "block"
}