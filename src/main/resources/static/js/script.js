console.log("this is scripting")
const toggleSidebar =()=>{
    if($('.sidebar').is(":visible")){
        $('.sidebar').css("display","none");
        $('.content').css("margin-left","0%");
    }
    else{
        $('.sidebar').css("display","block");
        $('.content').css("margin-left","20%");
    }

};

const search =()=>{
    let query=$("#search-input").val();
    if(query == ''){$(".search-result").hide();}
    else{
        console.log(query);
        let url=`http://localhost:8080/search/${query}`;
        fetch(url).then(
            (response)=>{return response.json();}
        ).then(
            (data)=>{
                console.log("DATA",data);
            let text=`<div class='list-group'>`;
            data.forEach((contact) => {
                text +=`<a href='/user/${contact.c_Id}/contact' class='list-group-item list-group-item-action'><img class="my-profile-pic" src="/img/${contact.image}">  ${contact.name}</a>`
            });
            text +=`</div>`;
            $(".search-result").html(text);  
            $(".search-result").show();  
            }
            );  
    }

};
//<td><img src="" th:if="${c.image}" class="my-profile-pic" th:src="@{'/img/'+${c.image}}" alt="image"><span th:text="${c.name}"></span></td>