import {useState, useEffect} from "react";
import KidsService from "../API/KidsService";
import Loader from "../UI/Loader/Loader";
import React from "react";

function KidBrothersAndSisters({id, tokens, setTokens}) {
    const [kids, setKids] = useState([]);
    const [loader, setLoader] = useState(true);
    useEffect(() => {
        const fetchKids = async () => {
            KidsService.getBrothersAndSisters(id, tokens, localStorage.setItem).then(data => {
                setKids(data);
                setLoader(false);
            })
        };
        fetchKids();
    }, [id]);

    return (
        <div>
            {loader ? (
                <div style={{display: 'flex', alignItems: 'center', justifyContent: 'center'}}>
                    <Loader/>
                </div>
            ) : (
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Birth Year</th>
                        <th>Group name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {kids.map((kid) => (
                        <tr key={kid.id}>
                            <td>{kid.name}</td>
                            <td>{kid.birthYear}</td>
                            <td>{kid.groupName}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
};

export default KidBrothersAndSisters;