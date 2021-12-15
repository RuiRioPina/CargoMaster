/* Validando o facto de nao se introduzir um container num ship com a capacidade excedida */
create or replace trigger test_number_containers_not_exceed_capacity
before insert or update of idContainer on funcionario
for each row
declare 
number_of_containers number(5);
capacity number(5);


begin
number_of_containers := func_getcontainersfromonemanifest(2);
capacity := func_getcontainersfromonemanifest(987654321);
if : capacity = number_of_containers then
raise_application_error(-20000,'TENTOU INTRODUZIR MAIS CONTENTORES DO QUE A CAPACIDADE DO NAVIO');
end if;
end;
/